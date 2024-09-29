import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {UserGuard} from "../auth-guards/user-guard/user.guard";
import {PostQuestionComponent} from "./components/post-question/post-question.component";
import {ViewsQuestionComponent} from "./components/views-question/views-question.component";
import {GetQuestionsByUseridComponent} from "./components/get-questions-by-userid/get-questions-by-userid.component";

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent, canActivate: [UserGuard] },
  { path: 'question', component: PostQuestionComponent },
  { path: 'question/:questionId', component: ViewsQuestionComponent }, // Corrected here
  { path: "my_questions", component: GetQuestionsByUseridComponent, canActivate: [UserGuard] }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
