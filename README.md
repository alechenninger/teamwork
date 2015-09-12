# teamwork

An attempt to making sharing data and events between disparate teams, domains, and databases a fun and fluid exercise in collaboration.

Imagine there are three teams.

A platform team understands integration software like Apache Camel, Enterprise Integration Patterns, and message brokers. A non trivial arena with a healthy level of depth and active development.

The remaining two teams have more business-domain-specific concerns: they have their own applications, but they serve the same broader organizational purpose, and so they see the obvious power in collaborating: sharing data and events. Camel alleviates much of their burden, but they can still dig themselves into a hole. And they'll still have to maintain infrastructure like message brokers: scale them, secure them, monitor them. They'll soon want error reporting and tooling to support them. Oh, by the way, that message broker is now obsolete...

An integration platform (and team to go with it) could play much the same role Camel plays in writing implementations of EIPs but in the scale of organizations: empower application teams to integrate without the potentially debilitating requirements of a low-level integration skillset and time to exercise it. Sure, it'd be great if everyone had these things. But time is an obvious hot commodity, and not everyone cares to learn these topics. The applications are mostly what they care about. And the inversely, some mostly care about the integration platform. There are plenty of problems to go around, no need for everyone to solve all of them!

Of course, at some point, application teams will have to get their hands dirty. They'll have to handle how foreign messages get into their own format, for example. Who else should? If the platform team has to, they'll have to scale to understanding every application that wishes to integrate enough to integrate it. We can do better.

So Teamwork tries to spread the work in a way that makes the most sense. The platform handles the messaging concerns. Application teams provide configuration and business logic based on what they want to produce or consume in the form of plugins, versioned, uploaded and activated at runtime, specific to a versioned message type:

1. "Producer plugins" which take messages their team produces of a particular type and returns a valid message to be shared with interested consumers. The platform team provides what "valid" is (say, a canonical data model per the popular EIP), and the messaging endpoints with which producing teams can send messages to.
2. "Consumer plugins" which take produced and validated messages of a particular type and return a format understandable by their application. Consumers can also provide a separate endpoint for Teamwork to send the resulting message to, as well as "filters" to prevent their plugins from ever seeing messages they don't care about.

Beyond that, there are some other knobs to tweak (some you could limit to only "admins" if you wanted). But that's the gist of it.

Plugins come in various forms. The most fundamental would be a Java .jar with an implementation of a simple interface implemented using Camel. We can get creative here though and potentially template quite a bit. Just need to apply some XSLT? Just upload that. Camel integrates with JVM hosted scripting languages. Does your team work in Ruby? Use that. Just want to provide an HTTP endpoint for your processing? Easy.

At least that's the idea!

