import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { AnswerService } from "../../user-service/answer-services/answer.service";
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-edit-answer',
  templateUrl: './edit-answer.component.html',
  styleUrls: ['./edit-answer.component.scss']
})
export class EditAnswerComponent implements OnInit {
  validateForm: FormGroup;
  selectedFile: File | null = null;
  imagePreview: string | null = null;
  answerId!: number;
  isLoading = false;
  errorMessage: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private answerService: AnswerService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.validateForm = this.fb.group({
      body: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      this.router.navigate(['/']); // or handle error appropriately
      return;
    }
    this.answerId = Number(id);
    this.loadAnswer();
  }

  loadAnswer() {
    this.isLoading = true;
    this.answerService.getAnswerById(this.answerId)
      .pipe(
        catchError(error => {
          console.error('Failed to load answer', error);
          this.errorMessage = 'Failed to load answer. Please try again.';
          return of(null);
        }),
        finalize(() => this.isLoading = false)
      )
      .subscribe(answer => {
        if (answer) {
          this.validateForm.patchValue({
            body: answer.body
          });
        }
      });
  }

  addAnswer() {
    if (this.validateForm.valid) {
      this.isLoading = true;
      this.errorMessage = null;

      let answerData: any;

      if (this.selectedFile) {
        // If there's a file, use FormData
        answerData = new FormData();
        answerData.append('body', this.validateForm.value.body);
        answerData.append('file', this.selectedFile);
      } else {
        // If no file, just send JSON
        answerData = {
          body: this.validateForm.value.body
        };
      }

      this.answerService.editAnswer(this.answerId, answerData)
        .pipe(
          catchError(error => {
            console.error('Failed to update answer', error);
            this.errorMessage = 'Failed to update answer. Please try again.';
            return of(null);
          }),
          finalize(() => this.isLoading = false)
        )
        .subscribe(response => {
          if (response) {
            this.validateForm.reset();
            this.selectedFile = null;
            this.imagePreview = null;
            this.router.navigate(['/user/dashboard']); // Adjust route as needed
          }
        });
    }
  }

  onFileSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.onload = () => this.imagePreview = reader.result as string;
      reader.readAsDataURL(file);
    } else {
      this.selectedFile = null;
      this.imagePreview = null;
    }
  }
}
