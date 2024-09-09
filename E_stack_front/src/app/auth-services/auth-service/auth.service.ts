import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, tap } from 'rxjs';
import { StorageService } from "../storage-service/storage.service";

const BASIC_URL = 'http://localhost:8080/';
export const AUTH_HEADER = "Authorization"; // Fixed typo

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
    private storage: StorageService
  ) { }

  sigunp(signupRequest: any): Observable<any> {
    return this.http.post(BASIC_URL + "signup", signupRequest);
  }

  login(email:string,password:string): Observable<any> {
    return this.http.post(BASIC_URL + "authentication", {
      email,password
      },
      { observe: "response" })
      .pipe(
        tap(() => this.log("User authenticated")),
        map((res: HttpResponse<any>) => {
          this.storage.saveUser(res.body);
          const authHeader = res.headers.get(AUTH_HEADER);
          if (authHeader) {
            const tokenLength = authHeader.length;
            const bearerToken = authHeader.substring(7, tokenLength);
            this.storage.saveToken(bearerToken);
          } else {
            console.error("Authorization header not found");
          }
          return res;
        })
      );
  }

  log(message: string) {
    console.log("User Auth Service : " + message);
  }
}
