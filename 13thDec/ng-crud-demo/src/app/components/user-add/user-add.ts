import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { Router } from '@angular/router';
import { UserService } from '../../services/user';

@Component({
  selector: 'app-user-add',
  standalone: true,
  imports: [CommonModule, FormsModule], 
  templateUrl: './user-add.html' 
})
export class UserAdd {
  user = { name: '', email: '' };

  constructor(private userService: UserService, private router: Router) {}

  addUser() {
  this.userService.addUser(this.user).subscribe({
    next: () => {
      alert('User added successfully!');
      this.router.navigate(['/users']);
    },
    error: (err) => {
      console.error('Error adding user:', err);
      alert('Failed to add user');
    }
  });
}
}