import { Component, OnInit } from '@angular/core';
import { ApprenantService } from '../../admin-service/apprenant.service';
import {QuestionService} from "../../../user/user-service/question-service/question.service"; // Adjust the path as necessary

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss']
})
export class AdminDashboardComponent implements OnInit {
  apprenantCount: number = 0; // Property to hold the count
  questionCount: number = 0; // Property to hold the question count

  constructor(private apprenantService: ApprenantService,
              private questionService : QuestionService) {}

  ngOnInit(): void {
    this.loadApprenantCount(); // Call the method to load the count
    this.loadQuestionCount();   // Call the method to load the question count
  }

  loadApprenantCount(): void {
    this.apprenantService.countApprenants().subscribe(
      count => this.apprenantCount = count, // Assign the count to the property
      error => console.error('Error loading apprenant count:', error) // Handle any errors
    );
  }
  loadQuestionCount(): void {
    this.questionService.countQuestions().subscribe(
      count => this.questionCount = count,
      error => console.error('Error loading question count:', error)
    );
}
}
