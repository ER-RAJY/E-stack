import { CanActivateFn } from '@angular/router';
import { StorageService } from '../../auth-services/storage-service/storage.service'; // Adjust the import path
import { Router } from '@angular/router';

// Create a function that returns the guard
export const roleGuard = (router: Router): CanActivateFn => {
  return (route, state) => {
    const storageService = new StorageService(); // Ensure you have access to StorageService
    const userRole = storageService.getUserRole();

    // Check for valid roles
    if (userRole === 'ADMIN' || userRole === 'APPRENANT') {
      return true; // Allow access if the role is valid
    } else {
      // Navigate to login or error page if no valid role
      router.navigate(['/login']);
      return false;
    }
  };
};
