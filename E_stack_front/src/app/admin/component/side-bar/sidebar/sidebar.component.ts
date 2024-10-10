import { Component, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {
  @ViewChild('drawer') drawer!: MatSidenav;  // Get the reference to the MatSidenav component

  isUserLoggedIn = true; // Or fetch from a service
  userRole = 'ADMIN';    // Or fetch from a service

  constructor(private router: Router) {}

  // Method to toggle the sidenav
  toggleDrawer(): void {
    if (this.drawer) {
      this.drawer.toggle();
    }
  }

  // Method to handle logout
  logout(): void {
    localStorage.removeItem('access_token');
    localStorage.removeItem('character_id');
    this.router.navigate(['/login']);
  }
}
