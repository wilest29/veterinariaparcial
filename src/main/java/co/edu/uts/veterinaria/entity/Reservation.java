package co.edu.uts.veterinaria.entity;

import javax.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserv_id", nullable = false)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "reserv_user", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "reserv_flight", nullable = false)
    private Flight flight;
    @Column(name = "reserv_payment")
    private String payment;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Flight getFlight() {
        return flight;
    }
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    public String getPayment() {
        return payment;
    }
    public void setPayment(String payment) {
        this.payment = payment;
    }
}