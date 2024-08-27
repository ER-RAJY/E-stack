import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


const BASIC_URL = 'http://localhost:8080/';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http : HttpClient){}
  sigunp(signupRequest:any):Observable<any>{
    return this.http.post(BASIC_URL+"signup",signupRequest)
  }
  login(loginRequest:any):Observable<any>{
    return this.http.post(BASIC_URL+"authentication",loginRequest)
  }
}
