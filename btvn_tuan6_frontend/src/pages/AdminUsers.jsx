import React, { useEffect, useState } from "react";
import axios from "axios";

export default function AdminUsers() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem("token");
    axios
      .get("/api/users", { headers: { Authorization: `Bearer ${token}` } })
      .then((r) => setUsers(r.data))
      .catch((e) => alert("Unauthorized or error"));
  }, []);

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Users</h1>

      <div className="overflow-x-auto rounded-lg shadow">
        <table className="min-w-full bg-white">
          <thead className="bg-gray-100 border-b">
            <tr>
              <th className="px-6 py-3 text-left text-sm font-semibold text-gray-700">
                ID
              </th>
              <th className="px-6 py-3 text-left text-sm font-semibold text-gray-700">
                Username
              </th>
              <th className="px-6 py-3 text-left text-sm font-semibold text-gray-700">
                Email
              </th>
              <th className="px-6 py-3 text-left text-sm font-semibold text-gray-700">
                Role
              </th>
            </tr>
          </thead>
          <tbody>
            {users.length > 0 ? (
              users.map((u) => (
                <tr key={u.id} className="border-b hover:bg-gray-50">
                  <td className="px-6 py-3 text-sm text-gray-800">{u.id}</td>
                  <td className="px-6 py-3 text-sm text-gray-800">
                    {u.username}
                  </td>
                  <td className="px-6 py-3 text-sm text-gray-800">{u.email}</td>
                  <td className="px-6 py-3 text-sm text-gray-800">{u.role}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td
                  colSpan="4"
                  className="text-center py-4 text-gray-500 italic"
                >
                  No users found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
