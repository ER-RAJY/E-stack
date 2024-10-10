import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Apprenant } from '../../model/Apprenant'; // Create this interface in your models folder

@Injectable({
  providedIn: 'root'
})
export class ApprenantService {

  // Define baseUrl as a class property
  private readonly baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  // Method to get all apprenants
  getApprenants(): Observable<Apprenant[]> {
    return this.http.get<Apprenant[]>(`${this.baseUrl}/all`);
  }

  // Method to get apprenant by ID
  getApprenantById(id: number): Observable<Apprenant> {
    return this.http.get<Apprenant>(`${this.baseUrl}/getApprenants/${id}`);
  }

  // Method to add an apprenant
  addApprenant(apprenant: Apprenant): Observable<any> {
    return this.http.post(`${this.baseUrl}/add`, apprenant);
  }

  // Method to update apprenant details
  updateApprenant(id: number, apprenant: Apprenant): Observable<Apprenant> {
    return this.http.put<Apprenant>(`${this.baseUrl}/edit/${id}`, apprenant);
  }

  // Method to delete an apprenant
  deleteApprenant(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
  }
}
