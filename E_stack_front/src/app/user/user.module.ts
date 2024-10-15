import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserRoutingModule } from './user-routing.module';
import { PostQuestionComponent } from './components/post-question/post-question.component';
import { ViewsQuestionComponent } from './components/views-question/views-question.component';
import { GetQuestionsByUseridComponent } from './components/get-questions-by-userid/get-questions-by-userid.component';
import { DashboardComponent } from "./components/dashboard/dashboard.component";

// Import Angular Material Modules
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatInputModule } from "@angular/material/input";
import { MatChipsModule } from "@angular/material/chips";
import { MatIconModule } from "@angular/material/icon";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatPaginatorModule } from "@angular/material/paginator"; // Only keep this
import { MatCardModule } from "@angular/material/card";
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from "@angular/material/button";

@NgModule({
  declarations: [
    DashboardComponent,
    PostQuestionComponent,
    ViewsQuestionComponent,
    GetQuestionsByUseridComponent,
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    MatProgressSpinnerModule,
    MatInputModule,
    MatChipsModule,
    MatIconModule,
    MatPaginatorModule, // Use only MatPaginatorModule
    ReactiveFormsModule,
    MatCardModule,
    MatDialogModule,
    MatButtonModule,
    FormsModule,
  ]
})
export class UserModule { }
