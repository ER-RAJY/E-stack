import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  private static readonly ACCESS_TOKEN_KEY = 'access_token';
  private static readonly USER_ID_KEY = 'character_id';

  constructor() { }

  // Save the JWT token
  setToken(token: string): void {
    localStorage.setItem(StorageService.ACCESS_TOKEN_KEY, token);
  }

  // Retrieve the JWT token
  getToken(): string | null {
    return localStorage.getItem(StorageService.ACCESS_TOKEN_KEY);
  }

  // Check if a token exists
  hasToken(): boolean {
    return this.getToken() !== null;
  }

  // Save the user ID
  setUserId(userId: number): void {
    localStorage.setItem(StorageService.USER_ID_KEY, userId.toString());
  }

  // Retrieve the user ID
  getUserId(): number | null {
    const apprenantId = localStorage.getItem("character_id");
    return apprenantId ? Number(apprenantId) : null;
  }

  // Logout method
  logout(): void {
    this.clear(); // Clear all stored items
  }

  // Clear storage
  clear(): void {
    localStorage.removeItem(StorageService.ACCESS_TOKEN_KEY);
    localStorage.removeItem(StorageService.USER_ID_KEY);
  }
}
