import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router'; 

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule, RouterModule], 
  templateUrl: './user-list.html',
  styleUrl: './user-list.css'
})
export class UserList implements OnInit {
  users: any[] = [];

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getUsers().subscribe({
      next: (data: any) => {
        console.log("Data received:", data);
        this.users = data;
      },
      error: (err) => {
        console.error("Error fetching users:", err);
      }
    });
  }
  deleteUser(id: string | number) {
    if (confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUser(Number(id)).subscribe({
        next: () => {
          this.users = this.users.filter(u => u.id !== id);
          alert('User deleted successfully');
        },
        error: (err) => {
          console.error("Error deleting user:", err);
        }
      });
    }
  }
}