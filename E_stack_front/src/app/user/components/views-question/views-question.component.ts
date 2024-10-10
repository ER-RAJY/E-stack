import { Component, OnInit } from '@angular/core';
import { QuestionService } from '../../user-service/question-service/question.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { StorageService } from "../../../auth-services/storage-service/storage.service";
import { AnswerService } from "../../user-service/answer-services/answer.service";
import { MatDialog } from '@angular/material/dialog';
import { EditAnswerComponent } from "../edit-answer/edit-answer.component";
import Swal from "sweetalert2"; // Import MatDialog

@Component({
  selector: 'app-views-question',
  templateUrl: './views-question.component.html',
  styleUrls: ['./views-question.component.scss']
})
export class ViewsQuestionComponent implements OnInit {
  questionId: number = this.activatedRoute.snapshot.params["questionId"];
  question: any;
  validateForm!: FormGroup;
  selectedFile!: File | null;
  imagePreview!: string | ArrayBuffer | null;
  formData: FormData = new FormData();
  answers: any[] = [];
  displayButton: boolean = false;

  constructor(
    private questionService: QuestionService,
    private router: Router,
    private answerService: AnswerService,
    private activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    private snackBar: MatSnackBar,
    private storageService: StorageService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.validateForm = this.formBuilder.group({
      body: [null, Validators.required]
    });
    this.getQuestionById();
  }

  getQuestionById() {
    this.questionService.getQuestionById(this.questionId).subscribe(data => {
      console.log("Get Question By Id questionService", data);
      this.question = data.questionDTO;
      this.answers = []; // Clear the answers array before repopulating

      // Iterate over the answer list and ensure approved is correctly mapped
      data.answerDtoList.forEach((element: any) => {
        if (element.file != null) {
          element.convertedImg = "data:image/jpeg;base64," + element.file.data;
        }
        element.approved = element.approved || false; // Default to false if not present
        this.answers.push(element);
      });

      // Update displayButton logic if the user is the owner of the question
      if (this.storageService.getapprenantId() === this.question.apprenantId) {
        this.displayButton = true;
      }
    });
  }

  addAnswer() {
    const data = this.validateForm.value;
    data.questionId = this.questionId;
    data.apprenantId = this.storageService.getapprenantId();
    this.formData.append('multipartFile', this.selectedFile!);
    console.log("idQuestion===", data.questionId, "UserId===", data.apprenantId);
    this.answerService.postAnswer(data).subscribe(
      (res: { id: number | null; }) => {
        this.validateForm.reset();
        if (res.id != null) {
          this.answerService.postAnswerImage(this.formData, res.id).subscribe(
            (response: any) => {
              console.log("Answer image added", response);
            }
          );
          this.snackBar.open("Answer added successfully", "Close", {
            duration: 5000,
          });
          this.getQuestionById(); // Refresh the question and answers list
        } else {
          this.snackBar.open("Failed to add answer", "Close", {
            duration: 5000,
          });
        }
      },
      (error) => {
        this.snackBar.open("Failed to add answer", "Close", {
          duration: 5000,
        });
        console.error(error);
      }
    );
  }

  addVote(voteType: string, voted: number) {
    if (voted === 1 || voted === -1) {
      this.snackBar.open("You already voted for this question", "Close", {
        duration: 5000,
        panelClass: ['error-snackbar']
      });
    } else {
      const data = {
        apprenantId: this.storageService.getapprenantId(),
        questionId: this.questionId,
        voteType: voteType
      }
      this.questionService.addVoteToQuestion(data).subscribe(res => {
        console.log("********** addVote res : ", res);
        if (res != null) {
          this.snackBar.open("Vote added successfully", "Close", {
            duration: 5000,
          });
          this.getQuestionById();
        } else {
          this.snackBar.open("Vote added failed", "Close", {
            duration: 5000,
          });
        }
      });
    }
  }

  toEdit(answerId: number) {
    const dialogRef = this.dialog.open(EditAnswerComponent, {
      width: '400px',
      data: { answerId: answerId }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.snackBar.open("Answer updated successfully", "Close", {
          duration: 5000,
        });
        this.getQuestionById(); // Refresh the answers after edit
      } else {
        this.snackBar.open("Failed to update answer", "Close", {
          duration: 5000,
        });
      }
    });
  }

  approuveAnswer(answerId: number) {
    this.answerService.approuveAnswer(answerId).subscribe((res) => {
      console.log(res);
      if (res.id != null) {
        const hasApprovedAnswer = this.answers.find(answer => answer.id === answerId);
        if (hasApprovedAnswer) {
          hasApprovedAnswer.approved = true; // Update the approved status
        }

        this.snackBar.open("Answer approved successfully", "Close", { duration: 5000 });
      } else {
        this.snackBar.open("Something went wrong", "Close", { duration: 5000 });
      }
    });
  }

  onFileSelectedd(event: any) {
    this.selectedFile = event.target.files[0];
    this.previewImage();
    console.log("********** selected file", this.selectedFile);
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
    this.previewImage();
  }

  previewImage() {
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    };
    reader.readAsDataURL(this.selectedFile!);
  }

  deleteAnswer(answerId: number): void {
    // Use SweetAlert2 for confirmation
    Swal.fire({
      title: 'Are you sure?',
      text: 'You won’t be able to revert this!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#747474',
    }).then((result) => {
      if (result.isConfirmed) {
        // Proceed with deletion if confirmed
        this.answerService.deleteAnswer(answerId).subscribe(
          response => {
            console.log('Answer deleted:', response);
            this.snackBar.open("Answer deleted successfully", "Close", {
              duration: 5000,
            });
            this.getQuestionById(); // Refresh the question and answers list
          },
          error => {
            console.error('Error deleting answer:', error);
            this.snackBar.open("Failed to delete answer", "Close", {
              duration: 5000,
            });
          }
        );
      }
    });
  }

}
