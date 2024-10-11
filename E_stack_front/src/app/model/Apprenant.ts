// src/model/Apprenant.ts
export interface Apprenant {
  id: number;
  nom: string;
  email: string;
  password?: string; // Consider excluding this if not needed on the frontend
  role: string;
}
