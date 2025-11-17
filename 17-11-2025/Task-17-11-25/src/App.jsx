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
    console.log("Form Submitted:", data);
  };

  return (
    <div className="form-container">
      <h2 className="form-title">User Form</h2>

      <form onSubmit={handleSubmit(onSubmit)}>
        
        {/* TEXT FIELD */}
        <div className="form-group">
          <label className="form-label">Full Name:</label>
          <input
            className="form-input"
            type="text"
            {...register("name", {
              required: "Name is required",
            })}
          />
          {errors.name && <span className="error-message">{errors.name.message}</span>}
        </div>

        {/* DROPDOWN */}
        <div className="form-group">
          <label className="form-label">Select Country:</label>
          <select
            className="form-input"
            {...register("country", { required: "Country is required" })}
          >
            <option value="">-- Choose --</option>
            <option value="USA">USA</option>
            <option value="India">India</option>
            <option value="UK">UK</option>
          </select>
          {errors.country && <span className="error-message">{errors.country.message}</span>}
        </div>

        {/* RADIO BUTTONS */}
        <div className="form-group">
          <label className="form-label">Gender:</label>
          <div>
            <label>
              <input type="radio" value="Male" {...register("gender", { required: "Gender required" })} />
              Male
            </label>
            <label style={{ marginLeft: "20px" }}>
              <input type="radio" value="Female" {...register("gender")} />
              Female
            </label>
          </div>
          {errors.gender && <span className="error-message">{errors.gender.message}</span>}
        </div>

        {/* CHECKBOXES */}
        <div className="form-group">
          <label className="form-label">Skills:</label>
          <label>
            <input type="checkbox" value="HTML" {...register("skills")} /> HTML
          </label>
          <label style={{ marginLeft: "20px" }}>
            <input type="checkbox" value="CSS" {...register("skills")} /> CSS
          </label>
          <label style={{ marginLeft: "20px" }}>
            <input type="checkbox" value="JS" {...register("skills")} /> JavaScript
          </label>
        </div>

        {/* DATE INPUT */}
        <div className="form-group">
          <label className="form-label">Date of Birth:</label>
          <input
            className="form-input"
            type="date"
            {...register("dob", {
              required: "Date of birth required",
            })}
          />
          {errors.dob && <span className="error-message">{errors.dob.message}</span>}
        </div>

        {/* PASSWORD FIELD WITH STRONG VALIDATION */}
        <div className="form-group">
          <label className="form-label">Password:</label>
          <input
            className="form-input"
            type="password"
            {...register("password", {
              required: "Password is required",
              minLength: { value: 8, message: "Min length is 8" },
              pattern: {
                value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])/,
                message:
                  "Must contain uppercase, lowercase, number & special char",
              },
            })}
          />
          {errors.password && (
            <span className="error-message">{errors.password.message}</span>
          )}
        </div>

        <button className="submit-button" type="submit">
          Submit
        </button>
      </form>
    </div>
  );
}

export default Form;