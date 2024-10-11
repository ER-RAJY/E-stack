import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Apprenant } from '../../../model/Apprenant';
import { ApprenantService } from '../../admin-service/apprenant.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-edit-apprenant-dialog',
  templateUrl: './edit-apprenant-dialog.component.html',
  styleUrls: ['./edit-apprenant-dialog.component.scss']
})
export class EditApprenantDialogComponent implements OnInit {
  apprenant: Apprenant;
  isLoading = false; // Loading state

  constructor(
    public dialogRef: MatDialogRef<EditApprenantDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Apprenant,
    private apprenantService: ApprenantService,
    private snackBar: MatSnackBar // Inject MatSnackBar for notifications
  ) {
    this.apprenant = { ...data }; // Make a copy of the apprenant data
  }

  ngOnInit(): void {}

  onSave(): void {
    this.isLoading = true; // Set loading state to true

    // Check if we're adding or updating
    if (this.apprenant.id) {
      this.apprenantService.updateApprenant(this.apprenant.id, this.apprenant).subscribe(
        () => {
          this.snackBar.open('Apprenant updated successfully!', 'Close', { duration: 2000 });
          this.dialogRef.close(this.apprenant); // Close the dialog and return the updated apprenant
        },
        error => {
          console.error('Error updating apprenant:', error);
          this.snackBar.open('Error updating apprenant.', 'Close', { duration: 2000 });
        },
        () => this.isLoading = false // Reset loading state
      );
    } else {
      this.apprenantService.addApprenant(this.apprenant).subscribe(
        (newApprenant: Apprenant) => {
          this.snackBar.open('Apprenant added successfully!', 'Close', { duration: 2000 });
          this.dialogRef.close(newApprenant); // Close the dialog and return the new apprenant
        },
        error => {
          console.error('Error adding apprenant:', error);
          this.snackBar.open('Error adding apprenant.', 'Close', { duration: 2000 });
        },
        () => this.isLoading = false // Reset loading state
      );
    }
  }

  onCancel(): void {
    this.dialogRef.close(); // Close the dialog without saving
  }
}
