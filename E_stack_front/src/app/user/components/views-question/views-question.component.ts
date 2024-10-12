import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { QuestionService } from '../../user-service/question-service/question.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { StorageService } from "../../../auth-services/storage-service/storage.service";
import { AnswerService } from "../../user-service/answer-services/answer.service";
import { MatDialog } from '@angular/material/dialog';
import { EditAnswerComponent } from "../edit-answer/edit-answer.component";
import Swal from "sweetalert2";

@Component({
  selector: 'app-views-question',
  templateUrl: './views-question.component.html',
  styleUrls: ['./views-question.component.scss']
})
export class ViewsQuestionComponent implements OnInit {
  @ViewChild('fileInput') fileInput!: ElementRef;

  questionId: number = this.activatedRoute.snapshot.params["questionId"];
  question: any;
  validateForm!: FormGroup;
  selectedFile!: File | null;
  imagePreview!: string | ArrayBuffer | null;
  formData: FormData = new FormData();
  answers: any[] = [];
  displayButton: boolean = false;
  searchQuestion: string = '';
  searchTag: string = '';

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
    this.questionService.getQuestionById(this.questionId).subscribe(
      data => {
        console.log("Get Question By Id questionService", data);
        this.question = data.questionDTO;
        this.answers = data.answerDtoList || [];

        // Process each answer
        this.answers.forEach(answer => {
          if (answer.file && answer.file.data) {
            answer.convertedImg = "data:image/jpeg;base64," + answer.file.data;
          }
          answer.approved = answer.approved || false;
        });

        // Update displayButton logic if the user is the owner of the question
        this.displayButton = this.storageService.getapprenantId() === this.question.apprenantId;
      },
      error => {
        console.error("Error fetching question:", error);
        this.snackBar.open("Failed to load question and answers", "Close", {
          duration: 5000,
        });
      }
    );
  }

  addAnswer() {
    if (this.validateForm.invalid) {
      this.snackBar.open("Please fill in all required fields", "Close", {
        duration: 5000,
      });
      return;
    }

    const data = this.validateForm.value;
    data.questionId = this.questionId;
    data.apprenantId = this.storageService.getapprenantId();

    this.answerService.postAnswer(data).subscribe(
      (res: { id: number | null; }) => {
        this.validateForm.reset();
        if (res.id != null) {
          if (this.selectedFile) {
            this.formData = new FormData();
            this.formData.append('multipartFile', this.selectedFile);
            this.answerService.postAnswerImage(this.formData, res.id).subscribe(
              (response: any) => {
                console.log("Answer image added", response);
                this.getQuestionById(); // Refresh after image upload
                this.resetFileInput();
              },
              (error) => {
                console.error("Failed to upload image", error);
                this.getQuestionById(); // Refresh even if image upload fails
              }
            );
          } else {
            this.getQuestionById(); // Refresh immediately if no image
          }
          this.snackBar.open("Answer added successfully", "Close", {
            duration: 5000,
          });
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
      this.questionService.addVoteToQuestion(data).subscribe(
        res => {
          console.log("addVote response:", res);
          if (res != null) {
            this.snackBar.open("Vote added successfully", "Close", {
              duration: 5000,
            });
            this.getQuestionById(); // Refresh to show updated vote count
          } else {
            this.snackBar.open("Vote addition failed", "Close", {
              duration: 5000,
            });
          }
        },
        error => {
          console.error("Error adding vote:", error);
          this.snackBar.open("Failed to add vote", "Close", {
            duration: 5000,
          });
        }
      );
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

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      this.previewImage();
    }
  }

  previewImage() {
    if (this.selectedFile) {
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  resetFileInput() {
    this.selectedFile = null;
    this.imagePreview = null;
    if (this.fileInput) {
      this.fileInput.nativeElement.value = '';
    }
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
