import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../auth-services/auth-service/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JWT } from '../../model/JWT';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  hidePassword = true;  // Password visibility toggle

  constructor(
    private service: AuthService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  // Toggle password visibility
  togglePasswordVisibility(): void {
    this.hidePassword = !this.hidePassword;
  }

  // Form submit logic
  submitForm(): void {
    if (this.loginForm.invalid) {
      console.log('Form is invalid');
      return;
    }

    this.service.login(this.loginForm.value).subscribe({
      next: (response: JWT) => {
        console.log('Login successful:', response);
        const jwtToken = response.access_token;

        if (jwtToken) {
          localStorage.setItem('access_token', jwtToken);

          // Save other user details, such as apprenantId or characterId
          const apprenantId = response.character_id;
          if (apprenantId) {
            localStorage.setItem('character_id', apprenantId.toString());
          }

          // Navigate to home after successful login
          this.router.navigate(['/user/dashboard']);
        } else {
          console.error('Login failed: Invalid token');
        }
      },
      error: (error) => {
        console.error('Login error:', error);
        // Optionally, show a notification or an error message
      }
    });
  }
}
