import {Component, inject, OnInit} from '@angular/core';
import {QuestionService} from "../../user-service/question-service/question.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatChipEditedEvent, MatChipInputEvent} from "@angular/material/chips";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import { MatSnackBar } from '@angular/material/snack-bar';
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {Router} from "@angular/router";

@Component({
  selector: 'app-post-question',
  templateUrl: './post-question.component.html',
  styleUrls: ['./post-question.component.scss']
})
export class PostQuestionComponent implements OnInit{
  tags: any[] = [];
  isSubmitting!: boolean;
  addOnBlur: boolean = true;
  validateForm!: FormGroup;
  constructor(private service : QuestionService,
              private formBuilder: FormBuilder,
              private snackBar: MatSnackBar,
              private router : Router
  ) { }


  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  announcer = inject(LiveAnnouncer);


  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Check for duplicates
    if (value && this.tags.some(tag => tag.name.toLowerCase() === value.toLowerCase())) {
      this.snackBar.open(`Tag "${value}" already exists!`, 'Close', {
        duration: 3000,
        horizontalPosition: 'center',
      });
      // Clear the input value
      event.chipInput!.clear();
      return;
    }

    // Add our tag if it's not a duplicate
    if (value) {
      this.tags.push({ name: value });
    }

    // Clear the input value
    event.chipInput!.clear();
  }


  remove(tag: any): void {
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags.splice(index, 1);
      this.announcer.announce(`Removed ${tag}`);
    }
  }

  edit(tag: any, event: MatChipEditedEvent) {
    const value = event.value.trim();
    // Remove tag if it no longer has a name
    if (!value) {
      this.remove(tag);
      return;
    }
    // Edit existing tag
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags[index].name = value;
    }
  }


  ngOnInit() {
    this.validateForm = this.formBuilder.group({
      title: ['', Validators.required],
      body: ['', Validators.required],
      tags: ['', Validators.required],
    })
  }

  postQuestion(){
    console.log(this.validateForm.value);
    this.service.postQuestion(this.validateForm.value).subscribe({
      next: (res) => {
        console.log(res);
        if(res.id != null ){
          this.snackBar.open("Question posted successfully!", "close", {duration: 5000});
          this.router.navigate(["/user/dashboard"]);
        } else {
          this.snackBar.open("Something went wrong!", "close", {duration: 5000});
        }
      },
      error: (err) => {
        console.error('Error posting question:', err);
        this.snackBar.open("Failed to post question. Please try again.", "close", {duration: 5000});
      }
    });

  }

}
