% calculates Availability of a path
function avail = calculateAvailability(Avail, path)
    avail = 1;
    for i = 1:(length(path) - 1)
        avail = avail * Avail(path(i), path(i+1));
    end
end