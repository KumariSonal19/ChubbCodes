import { Routes } from '@angular/router';
import { UserList } from './components/user-list/user-list';
import { UserAdd } from './components/user-add/user-add';

export const routes: Routes = [
    { path: '', redirectTo: 'users', pathMatch: 'full' },
    { path: 'users', component: UserList },
    { path: 'add', component: UserAdd }
];