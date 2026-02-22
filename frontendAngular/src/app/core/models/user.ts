export interface User {
  id?: string;
  username: string;
  email: string;
  roles: string[];
  friends: User[];
}

export interface UserDTO extends User {}