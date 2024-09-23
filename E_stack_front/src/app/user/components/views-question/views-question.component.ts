import { Component, OnInit } from '@angular/core';
import { QuestionService } from '../../user-service/question-service/question.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import {StorageService} from "../../../auth-services/storage-service/storage.service";

@Component({
  selector: 'app-views-question',
  templateUrl: './views-question.component.html',
  styleUrls: ['./views-question.component.scss']
})
export class ViewsQuestionComponent implements OnInit {

  questionId: number = this.activatedRoute.snapshot.params["questionId"];
  question: any;
  validateForm!: FormGroup;


  constructor(
    private questionService: QuestionService,
    private activatedRoute: ActivatedRoute,

  ) { }

  ngOnInit() {

    this.getQuestionById();
  }

  getQuestionById() {
    this.questionService.getQuestionById(this.questionId).subscribe(
      data => {
        console.log("Get Question By Id questionService", data);
        this.question = data.questionDTO;
      },
      error => {
        console.error("Error fetching question:", error);
        if (error.status === 403) {
          console.log("Forbidden error. Details:", error);
          console.log("Current user ID:", StorageService.getUserId());
          console.log("Question ID:", this.questionId);
          // You might want to redirect to login or show a specific error message
        }
      }
    );
  }
}
