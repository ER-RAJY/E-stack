import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing.module';
import { AdminDashboardComponent } from './component/admin-dashboard/admin-dashboard.component';
import { ManageUsersComponent } from './component/manage-users/manage-users.component';
import { SidebarComponent } from './component/sidebar/sidebar.component';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from "@angular/material/card";
import { EditApprenantDialogComponent } from './component/edit-apprenant-dialog/edit-apprenant-dialog.component';
import { MatInputModule } from "@angular/material/input";
import { MatDialogModule } from "@angular/material/dialog";
import { FormsModule } from "@angular/forms";
import {MatSelectModule} from "@angular/material/select";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatTableModule} from "@angular/material/table";

@NgModule({
  declarations: [
    AdminDashboardComponent,
    ManageUsersComponent,
    SidebarComponent,
    EditApprenantDialogComponent,
    // Remove if not used
    // EditApprenantDialogComponentComponent,
  ],
  exports: [
    SidebarComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,

    // Angular Material modules
    MatIconModule,
    MatToolbarModule,
    MatListModule,
    MatSidenavModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatDialogModule,
    FormsModule,
    MatSelectModule,
    MatProgressBarModule,
    MatTableModule,
  ]
})
export class AdminModule { }
