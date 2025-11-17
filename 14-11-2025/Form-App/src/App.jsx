import React from "react";
import { useForm } from "react-hook-form";
import "./App.css";

function Form() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = (data) => {
    console.log("Event Registration Form Submitted:", data);
  };

  return (
    <div className="form-container">
      <h2 className="form-title">Event Registration</h2>
      <form onSubmit={handleSubmit(onSubmit)}>
        {/* Name Field with Required Validation */}
        <div className="form-group">
          <label className="form-label">Name:</label>
          <input
            className="form-input"
            type="text"
            {...register("name", {
              required: "Name is required", // Validation rule for required field
              maxLength: {
                value: 50,
                message: "Name cannot exceed 50 characters", // Max length validation
              },
            })}
          />
          {errors.name && (
            <span className="error-message">{errors.name.message}</span>
          )}
        </div>

        {/* Email Field with Required and Email Format Validation */}
        <div className="form-group">
          <label className="form-label">Email:</label>
          <input
            className="form-input"
            type="email"
            {...register("email", {
              required: "Email is required", // Required validation
              pattern: {
                value: /\S+@\S+\.\S+/,
                message: "Email format is invalid", // Email format validation
              },
            })}
          />
          {errors.email && (
            <span className="error-message">{errors.email.message}</span>
          )}
        </div>

        {/* Event Date Field with Date Format Validation */}
        <div className="form-group">
          <label className="form-label">Event Date:</label>
          <input
            className="form-input"
            type="datetime-local"
            {...register("eventDate", {
              required: "Event date is required", // Required validation
              validate: (value) => {
                const today = new Date().toISOString().split("T")[0];
                return value >= today || "Event date cannot be in the past";
              },
            })}
          />
          {errors.eventDate && (
            <span className="error-message">{errors.eventDate.message}</span>
          )}
        </div>

        {/* Age Field with Range Validation */}
        <div className="form-group">
          <label className="form-label">Age:</label>
          <input
            className="form-input"
            type="number"
            {...register("age", {
              required: "Age is required", // Required validation
              min: {
                value: 18,
                message: "You must be at least 18 years old",
              },
              max: {
                value: 100,
                message: "Age must be less than 100",
              },
            })}
          />
          {errors.age && (
            <span className="error-message">{errors.age.message}</span>
          )}
        </div>

        {/* Phone Number Field with Custom Validation */}
        <div className="form-group">
          <label className="form-label">Phone Number:</label>
          <input
            className="form-input"
            type="text"
            {...register("phone", {
              required: "Phone number is required", // Required validation
              pattern: {
                value: /^[0-9]{10}$/, // Ensure 10 digits
                message: "Phone number must be 10 digits",
              },
            })}
          />
          {errors.phone && (
            <span className="error-message">{errors.phone.message}</span>
          )}
        </div>

        {/* Number of Tickets Field with Custom Validation */}
        <div className="form-group">
          <label className="form-label">Number of Tickets:</label>
          <input
            className="form-input"
            type="number"
            {...register("tickets", {
              required: "Number of tickets is required", // Required validation
              min: {
                value: 1,
                message: "At least 1 ticket should be selected",
              },
              max: {
                value: 10,
                message: "You can purchase up to 10 tickets only",
              },
            })}
          />
          {errors.tickets && (
            <span className="error-message">{errors.tickets.message}</span>
          )}
        </div>

        <button className="submit-button" type="submit">
          Register
        </button>
      </form>
    </div>
  );
}

export default Form;