$(function() {
    $(".nav-chart1").click(function() {
        // display: block;
        $(".card-tmp").css("display", "block");
        $(".card-vcc").css("display", "none");
        $(".card-light").css("display", "none");
    })

    $(".nav-chart2").click(function() {
        // display: block;
        $(".card-tmp").css("display", "none");
        $(".card-vcc").css("display", "block");
        $(".card-light").css("display", "none");
    })

    $(".nav-chart3").click(function() {
        // display: block;
        $(".card-tmp").css("display", "none");
        $(".card-vcc").css("display", "none");
        $(".card-light").css("display", "block");
    })
})