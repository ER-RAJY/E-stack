import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from './component/admin-dashboard/admin-dashboard.component';
import { ManageUsersComponent } from "./component/manage-users/manage-users.component";
import { RoleGuard } from '../auth-guards/admin-gurad/role.guard'; // Ensure the name matches

const routes: Routes = [
  {
    path: '',
    canActivate: [RoleGuard], // Use the guard here
    children: [
      { path: 'dashboard', component: AdminDashboardComponent },
      { path: 'edit-users', component: ManageUsersComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
