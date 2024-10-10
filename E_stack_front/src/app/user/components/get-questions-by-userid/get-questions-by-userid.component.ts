import { Component } from '@angular/core';
import { QuestionService } from "../../user-service/question-service/question.service";
import { MatSnackBar } from '@angular/material/snack-bar';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-get-questions-by-userid',
  templateUrl: './get-questions-by-userid.component.html',
  styleUrls: ['./get-questions-by-userid.component.scss']
})
export class GetQuestionsByUseridComponent {

  questions: any[] = [];
  pageNumber: number = 0;
  total!: number;

  constructor(private questionService: QuestionService, private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.getAllQuestions();
  }

  getAllQuestions() {
    this.questionService.getQuestionsByUserId(this.pageNumber).subscribe(res => {
      console.log("res", res);
      this.questions = res.questionDTOList;
      this.total = res.totalPages * 5;
    });
  }

  pageIndexChange(event: any) {
    this.pageNumber = event.pageIndex;
    this.getAllQuestions();
  }

  // Method to delete a question
  deleteQuestion(questionId: number) {
    // Use SweetAlert2 for confirmation
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#747474',
      confirmButtonText: 'Yes, delete it!'
    }).then((result: { isConfirmed: any; }) => {
      if (result.isConfirmed) {
        this.questionService.deleteQuestion(questionId).subscribe(
          response => {
            console.log('Question deleted:', response);
            this.snackBar.open("Question deleted successfully", "Close", { duration: 5000 });

            // Remove the deleted question from the list without refreshing the page
            this.questions = this.questions.filter(question => question.id !== questionId);
          },
          error => {
            console.error('Error deleting question:', error);
            this.snackBar.open("Failed to delete question", "Close", { duration: 5000 });
          }
        );
      }
    });
  }
}
