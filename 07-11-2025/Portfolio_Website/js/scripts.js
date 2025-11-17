(function($) {
    $("#current-year").text(new Date().getFullYear());
    $('html').removeClass('no-js');
    $('header a').click(function(e) {
        if ($(this).hasClass('no-scroll')) return;
        e.preventDefault();
        var heading = $(this).attr('href');
        var scrollDistance = $(heading).offset().top;
        $('html, body').animate({
            scrollTop: scrollDistance + 'px'
        }, Math.abs(window.pageYOffset - $(heading).offset().top) / 1);
        if ($('header').hasClass('active')) {
            $('header, body').removeClass('active');
        }
    });
    $('#to-top').click(function() {
        $('html, body').animate({
            scrollTop: 0
        }, 500);
    });
    $('#lead-down span').click(function() {
        var scrollDistance = $('#lead').next().offset().top;
        $('html, body').animate({
            scrollTop: scrollDistance + 'px'
        }, 500);
    });
    $('#experience-timeline').each(function() {
        $this = $(this);
        $userContent = $this.children('div');
        $userContent.each(function() {
            $(this).addClass('vtimeline-content').wrap('<div class="vtimeline-point"><div class="vtimeline-block"></div></div>');
        });
        $this.find('.vtimeline-point').each(function() {
            $(this).prepend('<div class="vtimeline-icon"><i class="fa fa-map-marker"></i></div>');
        });

        // Add dates to the timeline if exists
        $this.find('.vtimeline-content').each(function() {
            var date = $(this).data('date');
            if (date) { // Prepend if exists
                $(this).parent().prepend('<span class="vtimeline-date">'+date+'</span>');
            }
        });

    });

    // Open mobile menu
    $('#mobile-menu-open').click(function() {
        $('header, body').addClass('active');
    });

    // Close mobile menu
    $('#mobile-menu-close').click(function() {
        $('header, body').removeClass('active');
    });

    // Load additional projects
    $('#view-more-projects').click(function(e){
        e.preventDefault();
        $(this).fadeOut(300, function() {
            $('#more-projects').fadeIn(300);
        });
    });

})(jQuery);

document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("contact-form");
  const statusMsg = document.getElementById("form-status");
  form.addEventListener("submit", function (e) {
    e.preventDefault();
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const message = document.getElementById("message").value.trim();

    if (!name || !email || !message) {
      statusMsg.textContent = "Please fill in all fields.";
      statusMsg.style.color = "red";
      return;
    }
    statusMsg.textContent = "Sending message...";
    statusMsg.style.color = "#333";
    setTimeout(() => {
      statusMsg.textContent = "Message sent successfully!";
      statusMsg.style.color = "green";
      form.reset();
    }, 1500);
  });
});
