import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, tap } from 'rxjs';
import { StorageService } from "../storage-service/storage.service";
import {JWT} from "../../model/JWT";

const BASIC_URL = 'http://localhost:8080/api/auth/';
export const AUTH_HEADER = "Authorization";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private httpClient: HttpClient,
    private storage: StorageService
  ) { }

  sigunp(signupRequest: any): Observable<any> {
    return this.httpClient.post(BASIC_URL + "signup", signupRequest);
  }


  login(loginRequest: any): Observable<JWT>{
    return this.httpClient.post<JWT>(BASIC_URL + 'login', loginRequest)
  };


  log(message: string) {
    console.log("User Auth Service : " + message);
  }
}
