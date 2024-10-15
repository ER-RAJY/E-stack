import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Apprenant } from '../../model/Apprenant'; // Assurez-vous que cette interface est correcte
import { StorageService } from '../../auth-services/storage-service/storage.service';

@Injectable({
  providedIn: 'root'
})
export class ApprenantService {
  private readonly baseUrl = 'http://localhost:8080/api/apprenants'; // Assurez-vous que le chemin est correct

  constructor(private http: HttpClient, private storageService: StorageService) {}

  private createAuthorizationHeader(): HttpHeaders {
    const token = this.storageService.getToken();
    console.log('Current token:', token); // Vérifiez ici si le token est bien récupéré
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json' // Assurez-vous que le type est correct
    });
  }

  getApprenants(): Observable<Apprenant[]> {
    return this.http.get<Apprenant[]>(`${this.baseUrl}/all`, { headers: this.createAuthorizationHeader() }).pipe(
      tap(data => console.log('Fetched Apprenants:', data)),
      catchError(err => {
        console.error('Error fetching apprenants', err);
        return of([]); // Retourner un tableau vide en cas d'erreur
      })
    );
  }

  getApprenantById(id: number): Observable<Apprenant> {
    return this.http.get<Apprenant>(`${this.baseUrl}/getApprenants/${id}`, { headers: this.createAuthorizationHeader() });
  }

  addApprenant(apprenant: Apprenant): Observable<any> {
    return this.http.post(`${this.baseUrl}/add`, apprenant, { headers: this.createAuthorizationHeader() })
  }


  updateApprenant(id: number, apprenant: Apprenant): Observable<Apprenant> {
    return this.http.put<Apprenant>(`${this.baseUrl}/edit/${id}`, apprenant, { headers: this.createAuthorizationHeader() });
  }

  deleteApprenant(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`, { headers: this.createAuthorizationHeader() });
  }

  countApprenants(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/count`, { headers: this.createAuthorizationHeader() }).pipe(
      tap(count => console.log('Counted Apprenants:', count)),
      catchError(err => {
        console.error('Error counting apprenants', err);
        return of(0); // Retourner 0 en cas d'erreur
      })
    );
  }
}
