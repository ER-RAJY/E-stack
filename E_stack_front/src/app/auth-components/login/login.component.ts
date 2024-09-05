import { Component } from '@angular/core';
import {AuthService} from "../../auth-services/auth-service/auth.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm!: FormGroup;
  constructor(
    private service:AuthService,
    private fb:FormBuilder
  ) {}
  ngOnInit(){
    this.loginForm=this.fb.group({
      email:['',Validators.required],
      password:['',Validators.required],
    })
  }
  login(){
    console.log(this.loginForm.value);
    this.service.login(this.loginForm.value).subscribe((response)=>{
      console.log(response);
      });
  }
}
