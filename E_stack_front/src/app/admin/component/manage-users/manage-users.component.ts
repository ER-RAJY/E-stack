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
  filteredApprenants: Apprenant[] = [];
  searchTerm: string = '';  // New property to bind to the search input
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
        this.filteredApprenants = data;  // Initially show all apprenants
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
          this.filteredApprenants = this.filteredApprenants.filter(a => a.id !== id); // Update filtered list
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
        // Reload the list of apprenants to reflect the updated data
        this.loadApprenants();
      }
    });
  }

  openAddApprenantDialog(): void {
    const dialogRef = this.dialog.open(EditApprenantDialogComponent, {
      width: '400px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Reload the list to include the newly added apprenant
        this.loadApprenants();
      }
    });
  }

  // New method to filter apprenants based on search term
  filterApprenants(): void {
    this.filteredApprenants = this.apprenants.filter(apprenant =>
      apprenant.nom.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      apprenant.email.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }
}
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  private static readonly ACCESS_TOKEN_KEY = 'access_token';
  private static readonly USER_ID_KEY = 'character_id';

  constructor() { }

  // Save the JWT token
  setToken(token: string): void {
    localStorage.setItem(StorageService.ACCESS_TOKEN_KEY, token);
  }

  // Retrieve the JWT token
  getToken(): string | null {
    return localStorage.getItem(StorageService.ACCESS_TOKEN_KEY);
  }

  // Check if a token exists
  hasToken(): boolean {
    return this.getToken() !== null;
  }

  // Save the user ID
  setUserId(userId: number): void {
    localStorage.setItem(StorageService.USER_ID_KEY, userId.toString());
  }

  // Retrieve the user ID
  getapprenantId(): number | null {
    const apprenantId = localStorage.getItem("character_id");
    return apprenantId ? Number(apprenantId) : null;
  }

  // Logout method
  logout(): void {
    this.clear(); // Clear all stored items
  }

  // Clear storage
  clear(): void {
    localStorage.removeItem(StorageService.ACCESS_TOKEN_KEY);
    localStorage.removeItem(StorageService.USER_ID_KEY);
  }
  // In StorageService
  private static readonly ROLE_KEY = 'user_role'; // Add this line for role key

  setUserRole(role: string): void {
    localStorage.setItem(StorageService.ROLE_KEY, role);
  }

  getUserRole(): string | null {
    return localStorage.getItem(StorageService.ROLE_KEY);
  }

}


