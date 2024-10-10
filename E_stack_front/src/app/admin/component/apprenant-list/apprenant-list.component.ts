import { Component, OnInit } from '@angular/core';
import { ApprenantService } from '../../admin-service/apprenant.service'; // Adjust the path as necessary
import { Apprenant } from '../../../model/Apprenant'; // Adjust the path as necessary

@Component({
  selector: 'app-apprenant-list',
  templateUrl: './apprenant-list.component.html',
  styleUrls: ['./apprenant-list.component.scss']
})
export class ApprenantListComponent implements OnInit {
  apprenants: Apprenant[] = []; // Array to hold the list of apprenants
  errorMessage: string | null = null; // For error handling

  constructor(private apprenantService: ApprenantService) {}

  ngOnInit(): void {
    this.loadApprenants(); // Load the list of apprenants on initialization
  }

  loadApprenants(): void {
    this.apprenantService.getApprenants().subscribe(
      (data: Apprenant[]) => {
        this.apprenants = data; // Assign the fetched data to the apprenants array
      },
      (error) => {
        this.errorMessage = 'Error loading apprenants'; // Handle errors
        console.error(error); // Log the error for debugging
      }
    );
  }
}
