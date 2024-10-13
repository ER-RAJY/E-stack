import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable, of, tap} from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Apprenant } from '../../model/Apprenant'; // Create this interface in your models folder
import { StorageService } from '../../auth-services/storage-service/storage.service';

@Injectable({
  providedIn: 'root'
})
export class ApprenantService {
  private readonly baseUrl = 'http://localhost:8080/api/apprenants'; // Ensure the correct path

  constructor(private http: HttpClient, private storageService: StorageService) {}

  private createAuthorizationHeader(): HttpHeaders {
    const token = this.storageService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getApprenants(): Observable<Apprenant[]> {
    return this.http.get<Apprenant[]>(`${this.baseUrl}/all`, { headers: this.createAuthorizationHeader() }).pipe(
      tap(data => console.log('Fetched Apprenants:', data)), // Log data
      catchError(err => {
        console.error('Error fetching apprenants', err);
        return of([]); // Return an empty array on error
      })
    );
  }

  getApprenantById(id: number): Observable<Apprenant> {
    return this.http.get<Apprenant>(`${this.baseUrl}/getApprenants/${id}`, { headers: this.createAuthorizationHeader() });
  }


  addApprenant(apprenant: Apprenant): Observable<any> {
    const token = localStorage.getItem('access_token'); // Use the correct key for your token
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}` // Make sure this is set correctly
    });

    console.log('Adding Apprenant with headers:', headers); // Debug log to check headers

    return this.http.post(`${this.baseUrl}/add`, apprenant, { headers }).pipe(
      tap(response => {
        console.log('Response from server on add:', response); // Log server response
      }),
      catchError(err => {
        console.error('Error adding apprenant:', err); // Log error
        return of(null); // Handle error gracefully
      })
    );


  return this.http.post(`${this.baseUrl}/add`, apprenant, { headers });
  }
  updateApprenant(id: number, apprenant: Apprenant): Observable<Apprenant> {
    return this.http.put<Apprenant>(`${this.baseUrl}/edit/${id}`, apprenant, { headers: this.createAuthorizationHeader() });
  }

  deleteApprenant(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`, { headers: this.createAuthorizationHeader() });
  }
  // New method to count Apprenants
  countApprenants(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/count`, { headers: this.createAuthorizationHeader() }).pipe(
      tap(count => console.log('Counted Apprenants:', count)), // Log count
      catchError(err => {
        console.error('Error counting apprenants', err);
        return of(0); // Return 0 on error
      })
    );
  }
}
