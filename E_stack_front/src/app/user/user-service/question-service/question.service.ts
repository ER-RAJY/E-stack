import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { StorageService } from "../../../auth-services/storage-service/storage.service";
import { Observable } from "rxjs";

const BASIC_URL = "http://localhost:8080/";

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(private http: HttpClient) { }

  postQuestion(questionDto: any): Observable<any> {
    console.log("questionDto", questionDto);
    questionDto.userId = StorageService.getUserId();
    return this.http.post(`${BASIC_URL}api/questions`, questionDto, {
      headers: this.createAuthorizationHeader()
    });
  }

  getAllQuestions(pageNumber: number): Observable<any> {
    return this.http.get<[]>(`${BASIC_URL}api/questions/${pageNumber}`, {
      headers: this.createAuthorizationHeader()
    });
  }

  getQuestionById(questionId: number): Observable<any> {
    const token = StorageService.getToken();
    console.log('Token being used:', token);
    return this.http.get<any>(`${BASIC_URL}api/questions/question/${StorageService.getUserId()}/${questionId}`, {
      headers: this.createAuthorizationHeader()
    });
  }

  getQuestionsByUserId(pageNumber: number): Observable<any> {
    return this.http.get<[]>(`${BASIC_URL}api/questions/user/${StorageService.getUserId()}/${pageNumber}`,
      { headers: this.createAuthorizationHeader() });
  }


  addVoteToQuestion(VoteQuestionDto: any): Observable<any> {
    return this.http.post<[]>(BASIC_URL + 'vote', VoteQuestionDto,
      { headers: this.createAuthorizationHeader() })
  }

  createAuthorizationHeader() {
    let authHeaders = new HttpHeaders();
    return authHeaders.set(
      'Authorization', 'Bearer ' + StorageService.getToken()
    );
  }
}
