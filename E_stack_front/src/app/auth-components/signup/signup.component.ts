import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/auth-services/auth-service/auth.service';
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {error} from "@angular/compiler-cli/src/transformers/util";


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  signupForm!: FormGroup;  // Use non-null assertion operator to tell TypeScript it's always initialized

  constructor(
    private service: AuthService,
    private fb: FormBuilder,
    private sncakBar:MatSnackBar,
    private router:Router,
  ) {}

  ngOnInit(): void {
    this.signupForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]], // Added email validator
      password: ['', Validators.required],
      confirmpassword: ['', Validators.required],
    },{validator: this.confrmationValidator})
  }
 private confrmationValidator(fg:FormGroup){
    const password =fg.get('password')?.value;
    const confirmpassword =fg.get('confirmpassword')?.value;
    if (password!=confirmpassword){
      fg.get('confirmpassword')?.setErrors({passwordMismatch:true});
    }else
      fg.get('confirmpassword')?.setErrors(null);
  }


  signup(): void {
     console.log(this.signupForm.value);
      this.service.sigunp(this.signupForm.value).subscribe((response)=>{
        console.log(response);
        if (response.id!=null){
          this.sncakBar.open(
            "you are registre successfully!",
                    'Close',
            {duration:5000}
          );
          this.router.navigateByUrl('/login');
        } else{
          this.sncakBar.open(response.message,'Close',{duration:5000});
        }
      },(error:any)=>{
        this.sncakBar.open("Registratio is Failed  try again later ",'Close',{duration:5000})
      })
  }
}
