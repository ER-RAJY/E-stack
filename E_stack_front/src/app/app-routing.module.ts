import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth-components/login/login.component';
import { NoAuthGuard } from "./auth-guards/noAuth-guard/no-auth.guard";

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' }, // Redirect to login on empty path
  { path: 'login', component: LoginComponent, canActivate: [NoAuthGuard] },

  // Lazy loading UserModule
  { path: 'user', loadChildren: () => import("./user/user.module").then(m => m.UserModule) },

  // Lazy loading AdminModule
  { path: 'admin', loadChildren: () => import("./admin/admin.module").then(m => m.AdminModule) },

  // Wildcard route for handling 404
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
