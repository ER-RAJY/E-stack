import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from './component/admin-dashboard/admin-dashboard.component';
import {ManageUsersComponent} from "./component/manage-users/manage-users.component";

const routes: Routes = [
  {
    path: '',
    children: [
      { path: 'dashboard', component: AdminDashboardComponent },
      { path: 'edit-users', component: ManageUsersComponent },
     // { path: 'settings', component: SettingsComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
