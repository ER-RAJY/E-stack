import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog'; // Import MatDialog
import { ApprenantService } from '../../admin-service/apprenant.service'; // Adjust the path as necessary
import { Apprenant } from '../../../model/Apprenant'; // Adjust the path as necessary
import { EditApprenantDialogComponent } from '../edit-apprenant-dialog/edit-apprenant-dialog.component'; // Adjust the path as necessary

@Component({
  selector: 'app-apprenant-list',
  templateUrl: './apprenant-list.component.html',
  styleUrls: ['./apprenant-list.component.scss']
})
export class ApprenantListComponent implements OnInit {
  apprenants: Apprenant[] = [];
  errorMessage: string | null = null;
  loading: boolean = false; // For loading state

  constructor(private apprenantService: ApprenantService, private dialog: MatDialog) {} // Inject MatDialog

  ngOnInit(): void {
    this.loadApprenants();
  }

  loadApprenants(): void {
    this.loading = true; // Start loading
    this.apprenantService.getApprenants().subscribe(
      (data: Apprenant[]) => {
        this.apprenants = data;
        this.loading = false; // Stop loading
      },
      (error) => {
        this.errorMessage = 'Error loading apprenants';
        console.error(error);
        this.loading = false; // Stop loading even on error
      }
    );
  }

  // Method to open the dialog
  openAddApprenantDialog(): void {
    const dialogRef = this.dialog.open(EditApprenantDialogComponent, {
      width: '400px', // Set the width of the dialog
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Reload the apprenants after adding a new one
        this.loadApprenants();
      }
    });
  }
}
