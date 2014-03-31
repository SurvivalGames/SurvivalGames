        $(function () {
            $("#jslider").slider({
                value: 100
            });
            $("#jslider").on("slidechange", function (event, ui) {
                BlockParty.change_volume(ui);
            });
        });