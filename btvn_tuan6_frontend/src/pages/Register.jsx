import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Register() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const nav = useNavigate();

  const submit = async (e) => {
    e.preventDefault();
    try {
      await axios.post("/auth/register", {
        email,
        password,
        firstName,
        lastName,
      });
      alert("Register success! Please login.");
      nav("/");
    } catch (err) {
      alert(err.response?.data?.message || "Register failed");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-r from-pink-100 to-purple-100">
      <form
        onSubmit={submit}
        className="bg-white p-8 rounded-2xl shadow-lg w-96"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">Create Account</h2>
        <label className="block text-sm">First name</label>
        <input
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
          className="w-full p-2 border rounded mt-1 mb-4"
        />
        <label className="block text-sm">Last name</label>
        <input
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
          className="w-full p-2 border rounded mt-1 mb-4"
        />
        <label className="block text-sm">Email</label>
        <input
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="w-full p-2 border rounded mt-1 mb-4"
        />
        <label className="block text-sm">Password</label>
        <input
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          type="password"
          className="w-full p-2 border rounded mt-1 mb-6"
        />
        <button className="w-full py-2 rounded bg-purple-600 text-white font-semibold">
          Sign up
        </button>
        <p className="mt-4 text-center text-sm">
          Already have account?{" "}
          <a href="/" className="text-purple-600">
            Login
          </a>
        </p>
      </form>
    </div>
  );
}
