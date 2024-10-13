// user.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserRoutingModule } from './user-routing.module';
import { PostQuestionComponent } from './components/post-question/post-question.component';
import { ViewsQuestionComponent } from './components/views-question/views-question.component';
import { GetQuestionsByUseridComponent } from './components/get-questions-by-userid/get-questions-by-userid.component';
import { EditAnswerComponent } from './components/edit-answer/edit-answer.component';
import { DashboardComponent } from "./components/dashboard/dashboard.component";

// Import Angular Material Modules
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatInputModule } from "@angular/material/input";
import { MatChipsModule } from "@angular/material/chips";
import { MatIconModule } from "@angular/material/icon";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { MatLegacyPaginatorModule } from "@angular/material/legacy-paginator"; // Import MatPaginatorModule
import { MatCardModule } from "@angular/material/card";
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from "@angular/material/button";

@NgModule({
  declarations: [
    DashboardComponent,
    PostQuestionComponent,
    ViewsQuestionComponent,
    GetQuestionsByUseridComponent,
    EditAnswerComponent,

  ],
    imports: [
        CommonModule,
        UserRoutingModule,
        MatProgressSpinnerModule,
        MatInputModule,
        MatChipsModule,
        MatIconModule,
        ReactiveFormsModule,
        MatLegacyPaginatorModule, // Add this line to import MatPaginator
        MatCardModule,
        MatDialogModule,
        MatButtonModule,
        FormsModule,
    ]
})
export class UserModule { }
