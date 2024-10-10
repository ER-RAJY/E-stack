import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminDashboardComponent } from './component/admin-dashboard/admin-dashboard.component';
import { ManageUsersComponent } from './component/manage-users/manage-users.component';
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatListModule} from "@angular/material/list";
import {MatSidenavModule} from "@angular/material/sidenav";
import { ApprenantListComponent } from './component/apprenant-list/apprenant-list.component';
import { SidebarComponent } from './component/side-bar/sidebar/sidebar.component';
import {MatButtonModule} from "@angular/material/button";


@NgModule({
  declarations: [
    AdminDashboardComponent,
    ManageUsersComponent,
    ApprenantListComponent,
    SidebarComponent,
  ],
  exports: [
    SidebarComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MatIconModule,
    MatToolbarModule,
    MatListModule,
    MatSidenavModule,
    MatButtonModule
  ]
})
export class AdminModule { }
