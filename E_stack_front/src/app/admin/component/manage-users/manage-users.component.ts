import { Component, OnInit } from '@angular/core';
import { Apprenant } from "../../../model/Apprenant";
import { ApprenantService } from "../../admin-service/apprenant.service";
import { MatDialog } from '@angular/material/dialog';
import { EditApprenantDialogComponent } from '../edit-apprenant-dialog/edit-apprenant-dialog.component';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.scss']
})
export class ManageUsersComponent implements OnInit {
  apprenants: Apprenant[] = [];
  errorMessage: string | null = null;
  loading: boolean = false;

  constructor(private apprenantService: ApprenantService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadApprenants();
  }

  loadApprenants(): void {
    this.loading = true;
    this.apprenantService.getApprenants().subscribe(
      (data: Apprenant[]) => {
        this.apprenants = data;
        this.loading = false;
      },
      (error) => {
        this.errorMessage = 'Error loading apprenants';
        console.error(error);
        this.loading = false;
      }
    );
  }

  deleteApprenant(id: number) {
    if (confirm('Are you sure you want to delete this apprenant?')) {
      this.apprenantService.deleteApprenant(id).subscribe(
        () => {
          this.apprenants = this.apprenants.filter(a => a.id !== id);
        },
        error => {
          this.errorMessage = 'Failed to delete apprenant';
        }
      );
    }
  }

  editApprenant(apprenant: Apprenant): void {
    const dialogRef = this.dialog.open(EditApprenantDialogComponent, {
      width: '400px',
      data: apprenant
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const index = this.apprenants.findIndex(a => a.id === result.id);
        if (index !== -1) {
          this.apprenants[index] = result;
        }
      }
    });
  }
}
