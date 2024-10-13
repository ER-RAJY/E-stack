import { CanActivateFn, Router } from '@angular/router';
import { StorageService } from '../../auth-services/storage-service/storage.service'; // Adjust the import path

// Create a function that returns the guard
export const RoleGuard: CanActivateFn = (route, state) => {
  const storageService = new StorageService(); // Ensure you have access to StorageService
  const userRole = storageService.getUserRole();

  // Check for valid roles
  if (userRole === 'ADMIN' || userRole === 'APPRENANT') {
    return true; // Allow access if the role is valid
  } else {
    // Navigate to login or error page if no valid role
    const router = new Router(); // Adjust as needed for your implementation
    router.navigate(['/login']);
    return false;
  }
};
