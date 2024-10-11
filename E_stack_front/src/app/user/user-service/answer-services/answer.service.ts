import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { StorageService } from "../../../auth-services/storage-service/storage.service";
import { Observable } from "rxjs";

const BASIC_URL = 'http://localhost:8080/api/';

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  constructor(private http: HttpClient, private storageService: StorageService) { }
  private getapprenantId(): number | null {
    return this.storageService.getapprenantId(); // Use instance method
  }
  postAnswer(answerDto: any): Observable<any> {
    answerDto.apprenantId = this.getapprenantId();
    return this.http.post(BASIC_URL + 'answer', answerDto,
      { headers: this.createAuthorizationHeader() });
  }

  postAnswerImage(formData: FormData, answerId: number): Observable<any> {
    return this.http.post(`${BASIC_URL}image/${answerId}`, formData,
      { headers: this.createAuthorizationHeader() });
  }



  approuveAnswer(answerId: number): Observable<any> {
    return this.http.get<[]>(BASIC_URL + `answer/${answerId}`,
      { headers: this.createAuthorizationHeader() });
  }

  editAnswer(id: number, answerDto: any): Observable<any> {
    let headers = this.createAuthorizationHeader();

    // If answerDto is FormData, don't set Content-Type
    if (!(answerDto instanceof FormData)) {
      headers = headers.set('Content-Type', 'application/json');
    }

    return this.http.put(BASIC_URL + `answer/${id}`, answerDto, { headers });
  }

  deleteAnswer(answerId: number): Observable<any> {
    return this.http.delete(BASIC_URL + `answer/${answerId}`,
      { headers: this.createAuthorizationHeader(), responseType: 'text' }); // Specify 'text' response type to handle the string
  }


  getAnswerById(answerId: number): Observable<any> {
    return this.http.get<any>(BASIC_URL + `answer/${answerId}`);
  }

  private createAuthorizationHeader(): HttpHeaders {
    const token = this.storageService.getToken();
    if (!token) {
      throw new Error('No authentication token found');
    }
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }
}
