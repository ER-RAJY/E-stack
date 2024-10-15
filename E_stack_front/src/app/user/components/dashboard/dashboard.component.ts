import { Component } from '@angular/core';
import { QuestionService } from "../../user-service/question-service/question.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  questions: any[] = [];
  pageNumber: number = 0;
  total: number = 0;
  pageSize: number = 5;

  constructor(private questionService: QuestionService) { }

  ngOnInit() {
    this.getAllQuestions();
  }

  getAllQuestions() {
    // Make sure to pass both pageNumber and pageSize
    this.questionService.getAllQuestions(this.pageNumber, this.pageSize).subscribe(res => {
      console.log("res", res);
      this.questions = res.questionDTOList; // Update to use questionDTOList
      this.total = res.totalElements; // Update to get total number of questions
    });
  }

  pageIndexChange(event: any) {
    console.log("Paginator changed:", event);
    this.pageNumber = event.pageIndex; // Update pageNumber based on paginator's page index
    this.getAllQuestions(); // Call to get the new questions based on the new page index
  }
}
