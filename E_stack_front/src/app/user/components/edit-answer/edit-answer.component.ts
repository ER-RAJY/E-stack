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
      body: ['', Validators.required]  // Form control for answer body
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      this.router.navigate(['/']); // Navigate to home if no ID is found
      return;
    }
    this.answerId = Number(id);
    this.loadAnswer();  // Load existing answer data
  }

  loadAnswer() {
    this.isLoading = true;
    this.answerService.getAnswerById(this.answerId)
      .pipe(
        catchError(error => {
          console.error('Failed to load answer', error);
          this.errorMessage = 'Failed to load answer. Please try again.';
          return of(null);  // Return an empty observable to prevent app crash
        }),
        finalize(() => this.isLoading = false)
      )
      .subscribe(answer => {
        if (answer) {
          this.validateForm.patchValue({ body: answer.body });  // Populate form with answer data
        }
      });
  }

  // Method to handle form submission for editing an answer
  editAnswer() {
    if (this.validateForm.valid) {
      this.isLoading = true;
      this.errorMessage = null;

      let answerData: any;

      // Handling file upload scenario
      if (this.selectedFile) {
        answerData = new FormData();
        answerData.append('body', this.validateForm.value.body);
        answerData.append('file', this.selectedFile);
      } else {
        // Only send body if no file is selected
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
            // Clear form and file upload preview on success
            this.validateForm.reset();
            this.selectedFile = null;
            this.imagePreview = null;
            this.router.navigate(['/user/dashboard']);  // Navigate to dashboard
          }
        });
    }
  }

  // Method to handle file selection and preview
  onFileSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.onload = () => this.imagePreview = reader.result as string;  // Generate image preview
      reader.readAsDataURL(file);
    } else {
      this.selectedFile = null;
      this.imagePreview = null;  // Clear preview if no file is selected
    }
  }

  // Method to handle cancel action (Optional)
  onCancel() {
    this.router.navigate(['/user/dashboard']);  // Navigate back without saving changes
  }
}
