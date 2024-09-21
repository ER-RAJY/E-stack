import { Injectable } from '@angular/core';

const  TOKEN = 'c_token';
const USER ='c_user';
@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }
  static hasToken(): boolean {
    // Check if localStorage is defined before using it
    if (typeof localStorage !== 'undefined' && localStorage.getItem(TOKEN) == null) {
      return false;
    }
    return true;
  }
  public saveUser(user:any){
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(USER,JSON.stringify(user));

  }
  public saveToken(token: string){
    window.localStorage.removeItem(TOKEN);
    window.localStorage.setItem(TOKEN,token);
  }
   static getToken():string{
      // @ts-ignore
     return  localStorage.getItem(TOKEN);
   }
   static isUserLoggedIn(){
    if(this.getToken()== null ){
      return false;
    }
    return  true;
  }
  static logout(){
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(USER);
  }
}
