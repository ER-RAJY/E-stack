import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { StorageService } from '../../../app/auth-services/storage-service/storage.service'; // Import the StorageService

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private router: Router, private storageService: StorageService) {} // Inject StorageService

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.storageService.getToken(); // Use StorageService to get the token

    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          // Handle unauthorized access, possibly by logging out the user
          this.storageService.clear(); // Clear token and userId using StorageService
          this.router.navigate(['/login']);
        }
        return throwError(() => error);
      })
    );
  }
}
