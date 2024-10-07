// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { AuthService } from 'src/app/auth-services/auth-service/auth.service';
// import { MatSnackBar } from "@angular/material/snack-bar";
// import { Router } from "@angular/router";
//
// @Component({
//   selector: 'app-signup',
//   templateUrl: './signup.component.html',
//   styleUrls: ['./signup.component.scss']
// })
// export class SignupComponent implements OnInit {
//   signupForm!: FormGroup;
//   hidePassword = true;
//
//   constructor(
//     private service: AuthService,
//     private fb: FormBuilder,
//     private snackBar: MatSnackBar,
//     private router: Router,
//   ) {}
//
//   ngOnInit() {
//     this.signupForm = this.fb.group({
//       email: ['', [Validators.required, Validators.email]],
//       name: ['', Validators.required],
//       password: ['', Validators.required],
//       confirmPassword: ['', Validators.required]
//     }, { validator: this.checkPasswords });
//   }
//
//   togglePasswordVisibility() {
//     this.hidePassword = !this.hidePassword;
//   }
//
//   checkPasswords(group: FormGroup) {
//     const password = group.get('password')?.value;
//     const confirmPassword = group.get('confirmPassword')?.value;
//     return password === confirmPassword ? null : { passwordMismatch: true };
//   }
//
//   signup(): void {
//     if (this.signupForm.valid) {
//       console.log(this.signupForm.value);
//       this.service.sigunp(this.signupForm.value).subscribe(
//         (response) => {
//           console.log(response);
//           if (response.id != null) {
//             this.snackBar.open(
//               "You are registered successfully!",
//               'Close',
//               { duration: 5000 }
//             );
//             this.router.navigateByUrl('/login');
//           } else {
//             this.snackBar.open(response.message, 'Close', { duration: 5000 });
//           }
//         },
//         (error: any) => {
//           this.snackBar.open("Registration failed. Please try again later.", 'Close', { duration: 5000 });
//         }
//       );
//     }
//   }
// }
