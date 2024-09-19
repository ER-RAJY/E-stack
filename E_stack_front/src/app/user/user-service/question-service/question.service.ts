import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {StorageService} from "../../../auth-services/storage-service/storage.service";
import {Observable} from "rxjs";


const BASIC_URL = ["http://localhost:8080/"]
@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(private http : HttpClient) { }
  postQuestion(questionDto: any): Observable<any>{
    console.log("questionDto", questionDto);
    return this.http.post(BASIC_URL + 'api/question', questionDto,{
      headers:this.createAuthorizationHeadere()
    }
    );
  }
  createAuthorizationHeadere() {
    let authHeaders = new HttpHeaders();
    return authHeaders.set(
      'Authorization', 'Bearer ' + StorageService.getToken()
    )
  }
}
