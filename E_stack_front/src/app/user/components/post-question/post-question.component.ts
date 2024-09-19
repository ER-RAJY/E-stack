import {Component, inject} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {StorageService} from "../../../auth-services/storage-service/storage.service";
import {QuestionService} from "../../user-service/question-service/question.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatChipEditedEvent, MatChipInputEvent} from "@angular/material/chips";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {COMMA, ENTER} from "@angular/cdk/keycodes";

@Component({
  selector: 'app-post-question',
  templateUrl: './post-question.component.html',
  styleUrls: ['./post-question.component.scss']
})
export class PostQuestionComponent {
  tags: any[] = [];
  isSubmitting!: boolean;
  addOnBlur: boolean = true;
  validateForm!: FormGroup;

  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  announcer = inject(LiveAnnouncer);


  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    // Add our tags
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

  constructor(private service : QuestionService,
              private formBuilder: FormBuilder,
              ) { }
  ngOnInit() {
    this.validateForm = this.formBuilder.group({
      title: ['', Validators.required],
      body: ['', Validators.required],
      tags: ['', Validators.required],
    })
  }

  postQuestion(){
    console.log(this.validateForm.value)
    this.service.postQuestion(this.validateForm.value).subscribe((res)=>{
      console.log(res)
    })

  }
}
